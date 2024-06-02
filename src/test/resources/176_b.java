public class FAKECLASS{
    private void applyPaxHeadersToCurrentEntry(Map<String, String> headers) {
        /*
         * The following headers are defined for Pax.
         * atime, ctime, charset: cannot use these without changing TarArchiveEntry fields
         * mtime
         * comment
         * gid, gname
         * linkpath
         * size
         * uid,uname
         * SCHILY.devminor, SCHILY.devmajor: don't have setters/getters for those
         */
        for (Entry<String, String> ent : headers.entrySet()){
            String key = ent.getKey();
            String val = ent.getValue();
            if ("path".equals(key)){
                currEntry.setName(val);
            } else if ("linkpath".equals(key)){
                currEntry.setLinkName(val);
            } else if ("gid".equals(key)){
                currEntry.setGroupId(Integer.parseInt(val));
            } else if ("gname".equals(key)){
                currEntry.setGroupName(val);
            } else if ("uid".equals(key)){
                currEntry.setUserId(Integer.parseInt(val));
            } else if ("uname".equals(key)){
                currEntry.setUserName(val);
            } else if ("size".equals(key)){
                currEntry.setSize(Long.parseLong(val));
            } else if ("mtime".equals(key)){
                currEntry.setModTime((long) (Double.parseDouble(val) * 1000));
            } else if ("SCHILY.devminor".equals(key)){
                currEntry.setDevMinor(Integer.parseInt(val));
            } else if ("SCHILY.devmajor".equals(key)){
                currEntry.setDevMajor(Integer.parseInt(val));
            }
        }
    }
}