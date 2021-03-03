package me.jungho.demoinfleanrestapi.events;

import lombok.*;
import me.jungho.demoinfleanrestapi.accounts.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Event {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment;
    private Boolean offline;
    private Boolean free;
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    @ManyToOne
    private Account manager;

    public void update(){
        // Update free
        if (this.basePrice == 0 && this.maxPrice == 0){
            this.free = true;
        } else {
            this.free = false;
        }
        // update offline
        if (this.location == null || this.location.isBlank()){
            this.offline =false;
        } else {
            this.offline = true;
        }
//        if (StringUtils.hasText(location)){
//            offline = true;
//        } else {
//            offline = false;
//        }
    }

}
