public class FAKECLASS{
    protected void setName(String name) {
        if (name != null && getPlatform() == PLATFORM_FAT
            && name.indexOf("/") == -1) {
            name = name.replace('\\', '/');
        }
        this.name = name;
    }
}