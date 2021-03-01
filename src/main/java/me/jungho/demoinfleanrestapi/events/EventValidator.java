package me.jungho.demoinfleanrestapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {

    public void validate(EventDto eventDto, Errors errors){
        if (eventDto.getBasePrice()>eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0){
//            errors.rejectValue("basePrice", "wrongValue","BasePrice is wrong." );
//            errors.rejectValue("maxPrice", "wrongValue","MaxPrice is wrong." );
            errors.reject("wrongPrices", "Values of price is wrong."); // 글로벌 에러
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime())||
        endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime())||
        endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())){
            errors.rejectValue("endEventDateTime", "wrongValue", "EventTime is wrong");
            // 필드 에러
        }

        // todo beginEventDateTime
        // todo CloseEnrollmentDateTime
    }
}
