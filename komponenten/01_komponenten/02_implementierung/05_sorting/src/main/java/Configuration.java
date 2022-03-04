public enum Configuration {
    instance;

    public final String fileSeparator = System.getProperty("file.separator");
    public final String userDirectory = System.getProperty("user.dir");

    public final String pathToJavaArchive = userDirectory + fileSeparator + "component" + fileSeparator + "jar" + fileSeparator;
}