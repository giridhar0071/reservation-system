
package com.giridhar.reservation.controller;
import com.giridhar.reservation.config.BatchScheduler;
import com.giridhar.reservation.repository.AppointmentRepository;
import com.giridhar.reservation.repository.SlotAvailabilityRepository;
import com.giridhar.reservation.repository.WaitingListEntryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    private final BatchScheduler scheduler;
    private final SlotAvailabilityRepository slotRepo;
    private final AppointmentRepository apptRepo;
    private final WaitingListEntryRepository waitingListRepo;

    public BatchController(BatchScheduler scheduler,
                           SlotAvailabilityRepository slotRepo,
                           AppointmentRepository apptRepo,
                           WaitingListEntryRepository waitingListRepo) {
        this.scheduler = scheduler;
        this.slotRepo  = slotRepo;
        this.apptRepo  = apptRepo;
        this.waitingListRepo = waitingListRepo;
    }

    @PostMapping("/run")
    public ResponseEntity<String> runBatchNow() throws Exception {
        scheduler.scheduleFridayBatch();
        return ResponseEntity.ok("Batch job triggered");
    }

    @GetMapping("/waiting")
    public ResponseEntity<Long> getWaitingCount(@RequestParam UUID locationId) {

        long waiting = waitingListRepo.countByLocation_Id(locationId);
        return ResponseEntity.ok(waiting);
    }

}
