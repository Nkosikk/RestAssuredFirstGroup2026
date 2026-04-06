package constants;

import utils.ReadConfigPropertyFile;

public class Endpoints {
    // This class defines the API endpoints used in the tests.
    // The endpoints are read from a configuration property file using the ReadConfigPropertyFile utility class.
    public static String registerendpoint = ReadConfigPropertyFile.getProperty("api.registerendpoint");
    public static String loginendpoint = ReadConfigPropertyFile.getProperty("api.loginendpoint");
    public static String approveusersendpoint = ReadConfigPropertyFile.getProperty("api.approveusersendpoint");
    public static String adminendpoint = ReadConfigPropertyFile.getProperty("api.adminendpoint");
    public static String adminusersendpoint = ReadConfigPropertyFile.getProperty("api.adminusersendpoint");
    public static String roleendpoint = ReadConfigPropertyFile.getProperty("api.roleendpoint");
    public static String deleteuserendpoint = ReadConfigPropertyFile.getProperty("api.deleteuserendpoint");



}
