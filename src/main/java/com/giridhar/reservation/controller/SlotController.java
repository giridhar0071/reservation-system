package com.giridhar.reservation.controller;

import com.giridhar.reservation.dto.CreateSlotRequest;
import com.giridhar.reservation.model.SlotAvailability;
import com.giridhar.reservation.service.SlotService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/slots")
public class SlotController {
    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping
    public SlotAvailability create(@RequestBody CreateSlotRequest req) {
        return slotService.createSlot(
                req.getLocationId(), req.getDate(), req.getSlot(), req.getCapacity()
        );
    }

    @GetMapping
    public List<SlotAvailability> list(
            @RequestParam UUID locationId,
            @RequestParam LocalDate date
    ) {
        return slotService.listByLocationAndDate(locationId, date);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        slotService.deleteSlot(id);
    }
}
