package com.bhavath.tracker.enums;

import java.util.ArrayList;
import java.util.List;

public enum SmsPacketTypesEnum {
	normal_packet_upload_periodicity("normal_packet_upload_periodicity","set-cvp-perdicvalue=","Normal Packet upload periodicity","SET"),
	sleep_mode_packet_upload_periodicity("sleep_mode_packet_upload_periodicity","set-cvp-sleepmvalue=","Sleep mode packet upload periodicity","SET"),
	panic_Button_Press_Time("panic_Button_Press_Time","set-cvp-empancvalue=","Panic button press time","SET"),
	vhno_vehicle_number("vhno_vehicle_number","set-cvp-vhclno=","Vehicle number","SET"),
	setting_apn("setting_apn","set-cvp-apn=","Setting apn","SET"),
	server_primary_IP_IPDNS_name("server_primary_IP_IPDNS_name","set-cvp-servip1=","Server primary IP. IP/DNS name","SET"),
	server_secondary_IP_IPDNS_name("server_secondary_IP_IPDNS_name","set-cvp-servip2=","Server secondary IP. IP/DNS name","SET"),
	primary_port_number_5_digit("primary_port_number_5_digit","set-cvp-prt1=","Primary port number 5 digit","SET"),
	secondary_port_number_5_digit("secondary_port_number_5_digit","set-cvp-prt2=","Secondary port number 5 digit","SET"),
	panic_enabled_disable("panic_enabled_disable","set-cvp-panicsts=","Panic Enabled/Disable","SET"),
	device_Reset("device_Reset","set-cvp-#iot#reset##","Device Reset","SET"),
	valve_enable_disable("valve_enable_disable","set-cvp-valvests=","Valve Enable/Disable","SET"),
	acceleration_thresholds("acceleration_thresholds","set-cvp-accxyz=","Acceleration thresholds","SET"),
	gyro_thresholds("gyro_thresholds","set-cvp-gyrxyz=","gyro thresholds","SET"),
	setting_internal_battery_low_Threshold_and_High_Threshold("setting_internal_battery_low_Threshold_and_High_Threshold","set-cvp-inbatth=","Setting internal battery low Threshold and High Threshold","SET"),
	setting_External_battery_low_Threshold_and_High_Threshold("setting_External_battery_low_Threshold_and_High_Threshold","set-cvp-exbatth=","Setting External battery low Threshold and High Threshold","SET"),
	vid_vendor_id("vid_vendor_id","set-cvp-vid=","Vid vendor ID","SET"),
	firmware_version("firmware_version","set-cvp-fwvrsn=","Firmware version","SET"),
	hardware_version("hardware_version","set-cvp-hwvrsn=","Hardware version","SET"),
	control_command_center_phone_number("control_command_center_phone_number","set-cvp-ccno=","Control command center phone number","SET"),
	panic_button_periodicity_value("panic_button_periodicity_value","set-cvp-panicpvalue=","Panic button periodicity value","SET"),
	set_vehicle_over_speed_threshold_value("set_vehicle_over_speed_threshold_value","set-cvp-overspeed=","Set vehicle over speed threshold value","SET"),
	sms_rtc_configuration("sms_rtc_configuration","set-cvp-#iotrtc,","Sms rtc configuration","SET"),
	on_demand_health_packet("on_demand_health_packet","set-cvp-hlthper=","On Demand Health Packet","SET"),
	
	get_normal_periodicity_value("get_normal_periodicity_value","get-cvp-peridicvalue?","Get normal periodicity value","GET"),
	get_sleep_mode_periodicity_value("get_sleep_mode_periodicity_value","get-cvp-sleepmvalue?","Get sleep mode periodicity value","GET"),
	get_emergency_panic_button_press_time("Get_emergency_panic_button_press_time","get-cvp-empancvalue?","Get emergency panic button press time","GET"),
	get_device_vehicle_number("get_device_vehicle_number","get-cvp-vhclno?","Get device vehicle number","GET"),
	get_APN("get_APN","get-cvp-apn?","Get APN","GET"),
	get_primary_server_IP_IP_DNS_name("get_primary_server_IP_IP_DNS_name","get-cvp-servip1?","Get primary server IP (IP/DNS name)","GET"),
	get_Secondary_server_IP_IP_DNS_name("Get_Secondary_server_IP_IP_DNS_name","get-cvp-servip2?","Get Secondary server IP (IP/DNS name)","GET"),
	get_primary_port_number("get_primary_port_number","get-cvp-prt1?","Get primary port number","GET"),
	get_secondary_port_number("get_secondary_port_number","get-cvp-prt2?","Get secondary port number","GET"),
	get_panic_is_enabled_disabled("get_panic_is_enabled_disabled","get-cvp-panicsts?","Get panic is enabled/disabled","GET"),
	get_valve_is_enabled_disabled("get_valve_is_enabled_disabled","get-cvp-valvests?","Get valve is enabled/disabled","GET"),
	get_harsh_breaking_acceleration_low_max_threshold_value("get_harsh_breaking_acceleration_low_max_threshold_value","get-cvp-accxyz?","Get harsh breaking acceleration low -max threshold value","GET"),
	get_harsh_acceleration_low_max_threshold_value("get_harsh_acceleration_low_max_threshold_value","get-cvp-gyrxyz?","Get harsh acceleration low - max threshold value","GET"),
	get_External_Battery_low_Max_threshold_Voltage_value("get_External_Battery_low_Max_threshold_Voltage_value","get-cvp-exbatth?","Get External Battery low - Max threshold Voltage value","GET"),
	get_Inetrnal_Battery_low_Max_threshold_Voltage_value("get_Inetrnal_Battery_low_Max_threshold_Voltage_value","get-cvp-inbatth?","Get Inetrnal Battery low - Max threshold Voltage value","GET"),
	get_Vendor_ID("get_Vendor_ID","get-cvp-vid?","Get Vendor ID","GET"),
	get_Firmware_Version("get_Firmware_Version","get-cvp-fwvrsn?","Get Firmware Version","GET"),
	get_hardware_version("get_hardware_version","get-cvp-hwvrsn?","Get hardware version","GET"),
	get_command_control_center_number("get_command_control_center_number","get-cvp-ccno?","Get command control center number","GET"),
	get_panic_periodicity_value("get_panic_periodicity_value","get-cvp-panicpvalue?","Get panic periodicity value","GET"),
	get_device_overspeed_limit_threshold_value("get_device_overspeed_limit_threshold_value","get-cvp-overspeed?","Get device overspeed limit threshold value","GET"),
	get_complete_status_of_device("get_complete_status_of_device","get-cvp-currstat?","Get complete status of device","GET"),
	
	clear_normal_periodicity_value("clear_normal_periodicity_value","clr-cvp-perdicvalue;","Clear normal periodicity value","CLR"),
	clear_sleep_mode_periodicity_value("clear_sleep_mode_periodicity_value","clr-cvp-sleepmvalue;","Clear sleep mode periodicity value","CLR"),
	clear_emergency_Panic_button_periodicity_value("clear_emergency_Panic_button_periodicity_value","clr-cvp-empancvalue;","Clear Emergency Panic button periodicity value","CLR"),
	clear_Vehicle_number("clear_Vehicle_number","clr-cvp-vhclno;","Clear Vehicle number","CLR"),
	clear_Device_APN("clear_Device_APN","clr-cvp-apn;","Clear Device APN","CLR"),
	clear_primary_IP("clear_primary_IP","clr-cvp-servip1;","Clear primary IP","CLR"),
	clear_secondary_IP("clear_secondary_IP","clr-cvp-servip2;","Clear secondary IP","CLR"),
	clear_Primary_Port("clear_Primary_Port","clr-cvp-prt1;","Clear Primary Port","CLR"),
	clear_secondary_Port("clear_secondary_Port","clr-cvp-prt2;","Clear secondary Port","CLR"),
	clear_panic_alert("clear_panic_alert","clr-cvp-panicsts;","Clear panic alert","CLR"),
	clear_accelerometer_threshold_values("clear_accelerometer_threshold_values","clr-cvp-accxyz;","Clear accelerometer threshold Values ","CLR"),
	clear_gyro_thresholds_values("clear_gyro_threshold_values","clr-cvp-gyrxyz;","Clear gyro threshold  values","CLR"),
	clear_Internal_LOW_HIGH_threshold_values("clear_Internal_LOW_HIGH_threshold_values","clr-cvp-inbatth;","Clear Internal LOW-HIGH threshold values","CLR"),
	clear_Exetrnal_LOW_HIGH_threshold_values("clear_Exetrnal_LOW_HIGH_threshold_values","clr-cvp-exbatth;","Clear  Exetrnal LOW-HIGH threshold values","CLR"),
	clear_Vendor_ID("clear_Vendor_ID","clr-cvp-vid;","Clear Vendor ID","CLR"),
	clear_Firmware_Version("clear_Firmware_Version","clr-cvp-fwvrsn;","Clear Firmware Version","CLR"),
	clear_Hardware_Version("clear_Hardware_Version","clr-cvp-hwvrsn;","Clear Hardware Version","CLR"),
	clear_Command_Control_Center("clear_Command_Control_Center","clr-cvp-ccno;","Clear Command Control Center","CLR"),
	clear_Panic_Button_Periodicity_Value("clear_Panic_Button_Periodicity_Value","clr-cvp-panicpvalue;","Clear Panic Button Periodicity Value","CLR"),
	clear_Overspeed_limit_threshold_value("clear_Overspeed_limit_threshold_value","clr-cvp-overspeed;","Clear Overspeed limit threshold value","CLR");

	public String key;
	public String payload;
	public String description;
	public String commandType;

	SmsPacketTypesEnum(String key,String payload,String description,String commandType) {
		this.key = key;
		this.payload = payload;
		this.description = description;
		this.commandType = commandType;
	}

	public String getKey() {
		return key;
	}


	public String getPayload() {
		return payload;
	}


	public String getDescription() {
		return description;
	}

	public String getCommandType() {
		return commandType;
	}
	
	public static List<SmsPacketTypesEnum> getEnumNameForValue(String value){
		SmsPacketTypesEnum[] values = SmsPacketTypesEnum.values();
        //String enumValue = null;
        List<SmsPacketTypesEnum> enumKeys = new ArrayList<SmsPacketTypesEnum>();
        for(SmsPacketTypesEnum eachValue : values) {
            if (eachValue.getCommandType().equals(value)) {
              // enumKeys.add(eachValue.name());
            	enumKeys.add(eachValue);
            }
        }
        return enumKeys;
    }

	@Override
	public String toString() {
		return this.getKey();
	}
}
