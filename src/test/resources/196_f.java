public class FAKECLASS{
    public Map<String, Integer> getHeaderMap() {
        return this.headerMap == null ? null : new LinkedHashMap<String, Integer>(this.headerMap);
    }
}