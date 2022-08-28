package ru.gb.spring_test.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.gb.spring_test.servise.UserService;
import ru.gb.spring_test.soap.GetAllUsersRequest;
import ru.gb.spring_test.soap.GetAllUsersResponse;
import ru.gb.spring_test.soap.User;

@Endpoint
@RequiredArgsConstructor
public class UserEndpoint {

    private static final String NAMESPACE_URI = "http://www.mvg.com/spring/ws/users";
    private final UserService userService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers(@RequestPayload GetAllUsersRequest request){
        GetAllUsersResponse response = new GetAllUsersResponse();
        userService.findAllUsers().forEach(u -> {
            User user = new User();
            user.setUsername(u.getUsername());
            user.setPassword(u.getPassword());
            user.setEmail(u.getEmail());
            response.getUsers().add(user);
        });
        return response;
    }
}
