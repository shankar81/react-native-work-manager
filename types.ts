export interface ScheduleParams {
  uniqueName: string;
  type: 'SINGLE_SHOT' | 'PERIODIC';
  initialDelay: number;
  initialUnit: TimeUnit;
  repeatInterval: number;
  intervalUnit: TimeUnit;
  notificationTitle: string;
  notificationDesc: string;
}

export type TimeUnit = 'MILLISECONDS' | 'SECONDS' | 'MINUTES' | 'HOURS';
