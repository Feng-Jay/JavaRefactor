public class FAKECLASS{
  private boolean inferTemplatedTypesForCall(
      Node n, FunctionType fnType) {
    final ImmutableList<TemplateType> keys = fnType.getTemplateTypeMap()
        .getTemplateKeys();
    if (keys.isEmpty()) {
      return false;
    }

    // Try to infer the template types
    Map<TemplateType, JSType> inferred = Maps.filterKeys(
        inferTemplateTypesFromParameters(fnType, n),
        new Predicate<TemplateType>() {

          @Override
          public boolean apply(TemplateType key) {
            return keys.contains(key);
          }}
        );

    // Replace all template types. If we couldn't find a replacement, we
    // replace it with UNKNOWN.
    TemplateTypeReplacer replacer = new TemplateTypeReplacer(
        registry, inferred);
    Node callTarget = n.getFirstChild();

    FunctionType replacementFnType = fnType.visit(replacer)
        .toMaybeFunctionType();
    Preconditions.checkNotNull(replacementFnType);

    callTarget.setJSType(replacementFnType);
    n.setJSType(replacementFnType.getReturnType());

    return replacer.madeChanges;
  }
}