package payloadBuilder;

import org.json.simple.JSONObject;

public class APIPayloadBuilder {

    // This method is used to create a payload for logging in a user. It takes the user's email and password as parameters
    // and returns a JSONObject containing these details.
    public static JSONObject loginUserPayload(String email, String password) {

        JSONObject loginUser = new JSONObject();
        loginUser.put("email", email);
        loginUser.put("password", password);

        return loginUser;
    }

    // This method is used to create a payload for registering a new user. It takes the user's first name, last name, email, password, and group ID as parameters
    // and returns a JSONObject containing these details.
    public static JSONObject registerUserPayload(String firstName, String lastName, String email, String password, String groupId) {
        JSONObject registerUser = new JSONObject();
        registerUser.put("firstName", firstName);
        registerUser.put("lastName", lastName);
        registerUser.put("email", email);
        registerUser.put("password", password);
        registerUser.put("confirmPassword", password);
        registerUser.put("groupId", groupId);

        return registerUser;
    }

    // This method is used to create a payload for updating a user's role. It takes the new role as a parameter
    // and returns a JSONObject containing this detail.
    public static JSONObject updateUserPayload(String role) {

        JSONObject updateUser = new JSONObject();
        updateUser.put("role", role);

        return updateUser;
    }
}
