package by.bsu.advertisement.service.service.impl;

import by.bsu.advertisement.service.model.Device;
import by.bsu.advertisement.service.repository.AdvertisementRepository;
import by.bsu.advertisement.service.service.DeviceService;
import by.bsu.advertisement.service.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final static Integer ADVERTISEMENT_APPEAR_TIME_MINUTES = 5;
    static final int MINUTES_PER_HOUR = 60;
    static final int SECONDS_PER_MINUTE = 60;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    private final DeviceService deviceService;
    private final AdvertisementRepository advertisementRepository;

    @Scheduled(fixedRate = 2000)
    @Override
    public void changeAppearAfterStatus() {
        List<Device> deviceList = deviceService.getAllByIsActive(true);
        deviceList.forEach(device -> device.getAdvertisements().forEach(advertisement -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime adAttachTime = advertisement.getAttachTime();
            long minuteDifference = getTime(adAttachTime, now)[1];
            if (minuteDifference >= ADVERTISEMENT_APPEAR_TIME_MINUTES && advertisement.getIsAppear().equals(false)) {
                advertisement.setAttachTime(LocalDateTime.now());
                advertisement.setIsAppear(true);
                advertisementRepository.save(advertisement);
                log.info("HIDE ADVERTISEMENT: {}", Instant.now());
            }
        }));
    }

    @Scheduled(fixedRate = 3000)
    @Override
    public void changeAppearBeforeStatus() {
        List<Device> deviceList = deviceService.getAllByIsActive(true);
        deviceList.forEach(device -> device.getAdvertisements().forEach(advertisement -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime adAttachTime = advertisement.getAttachTime();
            long minuteDifference = getTime(adAttachTime, now)[1];
            Integer impressionPerHour = getDelay(device.getImpressionPerHour());
            if (minuteDifference >= impressionPerHour && advertisement.getIsAppear().equals(true)) {
                advertisement.setAttachTime(LocalDateTime.now());
                advertisement.setIsAppear(false);
                advertisementRepository.save(advertisement);
                log.info("ENROLL ADVERTISEMENT: {}", Instant.now());
            }
        }));
    }

    private Integer getDelay(Integer impressionPerHour) {
        int totalAmountOfAdvertisementTime = ADVERTISEMENT_APPEAR_TIME_MINUTES * impressionPerHour;
        return (MINUTES_PER_HOUR - totalAmountOfAdvertisementTime) / impressionPerHour;
    }

    private static long[] getTime(LocalDateTime dob, LocalDateTime now) {
        LocalDateTime today = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), dob.getHour(), dob.getMinute(), dob.getSecond());
        Duration duration = Duration.between(today, now);

        long seconds = duration.getSeconds();

        long hours = seconds / SECONDS_PER_HOUR;
        long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        long secs = (seconds % SECONDS_PER_MINUTE);

        return new long[]{hours, minutes, secs};
    }
}
