export interface ScheduleParams {
  uniqueName: string;
  type: 'SINGLE_SHOT' | 'PERIODIC';
  initialDelay: number;
  intervalDelayUnit: TimeUnit;
  repeatInterval?: number;
  intervalUnit?: TimeUnit;
  notificationTitle: string;
  notificationDesc: string;
  notificationColor?: string;
}

export type TimeUnit = 'MILLISECONDS' | 'SECONDS' | 'MINUTES' | 'HOURS';
