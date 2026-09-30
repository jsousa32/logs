package gfinnovation.logs._shared;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

public final class CheckChangesObject {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private CheckChangesObject() {
    }

    public static boolean hasChanges(final String from, final String to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Both snapshots are required to compare an update");
        }

        final var before = OBJECT_MAPPER.readTree(from);
        final var after = OBJECT_MAPPER.readTree(to);

        if (before == null || after == null || !before.isObject() || !after.isObject()) {
            return false;
        }

        return !before.equals(after);
    }

    public static List<FieldChange> getChanges(final String from, final String to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Both snapshots are required to compare an update");
        }

        final var before = OBJECT_MAPPER.readTree(from);
        final var after = OBJECT_MAPPER.readTree(to);

        if (before == null || after == null || !before.isObject() || !after.isObject()) {
            return List.of();
        }

        final var changes = new ArrayList<FieldChange>();
        collectChanges(before, after, "", changes);
        return List.copyOf(changes);
    }

    private static void collectChanges(
            final JsonNode before,
            final JsonNode after,
            final String path,
            final List<FieldChange> changes
    ) {
        if (Objects.equals(before, after)) {
            return;
        }

        if (isObjectOrMissing(before) && isObjectOrMissing(after)) {
            final var fields = new TreeSet<String>();
            if (before != null) {
                before.properties().forEach(entry -> fields.add(entry.getKey()));
            }
            if (after != null) {
                after.properties().forEach(entry -> fields.add(entry.getKey()));
            }

            if (!fields.isEmpty()) {
                for (final var field : fields) {
                    final var childPath = path.isEmpty() ? field : path + "." + field;
                    collectChanges(
                            before == null ? null : before.get(field),
                            after == null ? null : after.get(field),
                            childPath,
                            changes
                    );
                }
                return;
            }
        }

        changes.add(new FieldChange(path, before, after));
    }

    private static boolean isObjectOrMissing(final JsonNode value) {
        return value == null || value.isObject();
    }

    public record FieldChange(
            String field,
            JsonNode initialValue,
            JsonNode finalValue
    ) {
    }
}

