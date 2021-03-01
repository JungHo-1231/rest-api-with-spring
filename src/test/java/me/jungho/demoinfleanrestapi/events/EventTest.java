package me.jungho.demoinfleanrestapi.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void builder(){
        //given 
        Event event = Event.builder()
                .name("Inflearn Spring Rest api")
                .description("rest api development with spring")
                .build();
        assertThat(event).isNotNull();
        //when 
         
        //then 
    }


    @Test
    void javaBean(){
        //given
        String name = "Event";
        String description = "Spring";

        //when
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        //then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

    void testFree(){
        //given
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();

        //when
        event.update();

        //then
        assertThat(event.getFree()).isTrue();

        //given
         event = Event.builder()
                .basePrice(100)
                .maxPrice(0)
                .build();

        //when
        event.update();

        //then
        assertThat(event.getFree()).isFalse();



        //given
         event = Event.builder()
                .basePrice(0)
                .maxPrice(100)
                .build();

        //when
        event.update();

        //then
        assertThat(event.getFree()).isFalse();
    }

    @Test
    void testOffline(){
        //given
        Event event = Event.builder()
                .location("강남역 네이버 d2 스타텁 팩토리")
                .build();

        //when
        event.update();

        //then
        assertThat(event.getOffline()).isTrue();

        //given
        event = Event.builder()
                .build();

        //when
        event.update();

        //then
        assertThat(event.getOffline()).isFalse();
    }

    @ParameterizedTest(name = "{index} => a={0}, b={1}, c={2}")
//    @CsvSource({
//            "0, 0 , true",
//            "100, 0, false",
//            "0, 100, false"
//    })
    @MethodSource("parametersForTestFree")
    void testFreeWithParam(int basePrice, int maxPrice, boolean isFree){

        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        event.update();

        assertThat(event.getFree()).isEqualTo(isFree);
    }

    static Stream<Arguments> parametersForTestFree(){
        return Stream.of(
                Arguments.of(0,0, true),
                Arguments.of(100,0, false),
                Arguments.of(0,100, false),
                Arguments.of(100,200, false)
        );
    }

    Object[] paramsForTestFree(){
        return new Object[]{
                new Object[]{0,0,true},
                new Object[]{100,0,false},
                new Object[]{0,100,false},
                new Object[]{100,200,false},
        };
    }

    @ParameterizedTest(name = "{index} => a={0}, b={1}")
    @MethodSource("parametersForTestOffline")
    void tstOffline(String location, boolean isOffline){
        //given
        Event event = Event.builder()
                .location(location)
                .build();
        //when
        event.update();

        //then
        assertThat(event.getOffline()).isEqualTo(isOffline);

    }
    private static Stream<Arguments> parametersForTestOffline(){
        return Stream.of(
                Arguments.of("강남", true),
                Arguments.of(null, false),
                Arguments.of("  ", false)
        );
    }

}






















