package com.bhavath.tracker.jobs.scheduler;

public enum PrepaidMeterTimeZone {

  UTC("GMT"),
  PST("GMT-8"),
  CST("GMT-6");

  public final String utcTimeDifference;

  PrepaidMeterTimeZone(String utcTimeDifference) {
    this.utcTimeDifference = utcTimeDifference;
  }
}
