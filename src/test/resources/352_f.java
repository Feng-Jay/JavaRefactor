public class FAKECLASS{
    static int toJavaVersionInt(String version) {
        return toVersionInt(toJavaVersionIntArray(version, JAVA_VERSION_TRIM_SIZE));
    }
}