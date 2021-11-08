package solar.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import solar.server.domain.Monitoring;
import solar.server.service.MonitoringService;



@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:8080/monitoring")
@RestController
@RequestMapping("/monitoring")
public class MonitoringController {


    private final MonitoringService monitoringService;


    @GetMapping("")
    public Monitoring monitoring() {
        return monitoringService.monitoring();
    }

    // 패털 접기 동작 (Topic : panelOperation, message : ON/OFF)
    @GetMapping("/paneloperation")
    public void panelOperation(@RequestParam(value="panelOperation") String panelOperation) {
        monitoringService.senderMqtt("panelOperation", panelOperation);
    }

    // 청소 동작 (Topic : cleanerOperation, message : ON)
    @GetMapping("/cleaneroperation")
    public void cleanerOperation() {
        monitoringService.senderMqtt("cleanerOperation", "ON");
    }






}
