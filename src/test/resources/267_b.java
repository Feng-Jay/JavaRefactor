public class FAKECLASS{
    private boolean _hasCustomHandlers(JavaType t) {
        if (t.isContainerType()) {
            // First: value types may have both value and type handlers
            JavaType ct = t.getContentType();
            if (ct != null) {
                return (ct.getValueHandler() != null) || (ct.getTypeHandler() != null);
            // Second: map(-like) types may have value handler for key (but not type; keys are untyped)
            }
        }
        return false;
    }
}