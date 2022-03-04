public enum Configuration {
    instance;

    public final String fileSeparator = System.getProperty("file.separator");
    public final String userDirectory = System.getProperty("user.dir");

    public final String nameOfJavaArchive = "memory_stick.jar";
    public final String nameOfClass = "MemoryStick";

    public final String typeOfMemoryStick = "02";

    public final String nameOfSubFolder = "exchange_component_" + typeOfMemoryStick + fileSeparator + "jar";
    public final String subFolderPathOfJavaArchive = nameOfSubFolder + fileSeparator + nameOfJavaArchive;
    public final String fullPathToJavaArchive = userDirectory + subFolderPathOfJavaArchive;

    public void print() {
        System.out.println("--- Configuration");
        System.out.println("fileSeparator              | " + fileSeparator);
        System.out.println("userDirectory              | " + userDirectory);
        System.out.println("typeOfMemoryStick          | " + typeOfMemoryStick);
        System.out.println("nameOfSubFolder            | " + nameOfSubFolder);
        System.out.println("nameOfJavaArchive          | " + nameOfJavaArchive);
        System.out.println("subFolderPathOfJavaArchive | " + subFolderPathOfJavaArchive);
        System.out.println("fullPathToJavaArchive      | " + fullPathToJavaArchive);
        System.out.println("nameOfClass                | " + nameOfClass);
        System.out.println();
    }
}