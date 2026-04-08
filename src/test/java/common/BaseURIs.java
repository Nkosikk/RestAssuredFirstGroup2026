package common;

import utils.ReadConfigPropertyFile;

public class BaseURIs {
    // Base URI for the API
    public static String baseURI = ReadConfigPropertyFile.getProperty("api.baseuri");
}
