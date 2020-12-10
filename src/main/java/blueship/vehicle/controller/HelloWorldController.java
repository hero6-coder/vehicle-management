package blueship.vehicle.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@Api(value = "hello", description = "Api service test")
public class HelloWorldController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @RequestMapping(value = "", method = RequestMethod.GET, produces = {
    MediaType.APPLICATION_JSON_VALUE})
  public String sayHello() {
    return "Hi you!!!";
  }
}
