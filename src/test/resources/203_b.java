public class FAKECLASS{
  public JsonWriter value(double value) throws IOException {
    writeDeferredName();
    if (Double.isNaN(value) || Double.isInfinite(value)) {
      throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
    }
    beforeValue();
    out.append(Double.toString(value));
    return this;
  }
}