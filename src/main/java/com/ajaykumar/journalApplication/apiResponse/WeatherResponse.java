package com.ajaykumar.journalApplication.apiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {
    public Current current;

    @Data
    public class Current{
        public int temperature;
        @JsonProperty("weather_descriptions")
        public List<String> weatherDescriptions;
        @JsonProperty("feelslike")
        public int feelsLike;
    }
}
