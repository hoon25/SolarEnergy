package solar.server.controller;


import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solar.server.service.PanelStatusService;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:8080/panelstatus")
@RequestMapping("/panelstatus")
@RestController
public class PanelStatusController {

    private final PanelStatusService panelStatusService;

    @GetMapping("")
    public @ResponseBody byte[] getImageWithMediaType() throws IOException {
        InputStream in = getClass().getResourceAsStream("/dog.png");
        System.out.println(in);
        return IOUtils.toByteArray(in);

    }


}
