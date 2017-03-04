package schema;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by Lloyd on 04/03/2017.
 */
@JsonPropertyOrder
public class FlightData {
    public String timestamp;
    public String content_airframe_registration;
    public String content_airframe_type_icao_value;
    public String content_airframe_wtc;
    public String content_arrival_aerodrome_actual_icao_value;
    public String content_arrival_aerodrome_scheduled_icao_value;
    public String content_arrival_time_inBlock_actual;
    public String content_arrival_time_landing_estimated;
    public String content_arrival_time_landing_scheduled;
    public String content_carrier_icao_quality;
    public String content_carrier_icao_value;
    public String content_departure_aerodrome_actual_icao_value;
    public String content_departure_aerodrome_scheduled_icao_value;
    public String content_departure_time_offBlock_estimated;
    public String content_departure_time_takeoff_actual;
    public String content_departure_time_takeoff_planned;
    public String content_departure_time_takeoff_scheduled;
    public String content_gufi;
    public String content_identification_callsign_quality;
    public String content_identification_callsign_value;
    public String content_identification_iataFlightNumber_quality;
    public String content_identification_iataFlightNumber_value;
    public String content_identification_squawk;
    public float content_movement_altitude_gps;
    public float content_movement_direction_heading;
    public float content_movement_position_lat;
    public float content_movement_position_lon;
    public String content_movement_speed_ground;
    public String content_movement_timestamp;
    public String content_movement_type;
    public String content_route_cruisingAltitude;
    public String content_route_cruisingSpeed;
    public String content_route_expanded;
    public String content_route_initialFlightRules;
    public String content_route_text;
    public String content_source;
    public String content_status;

    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
