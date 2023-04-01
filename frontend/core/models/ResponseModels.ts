export interface GenericResponse<T> {
  success: boolean;
  data: T;
  timestamp: Date;
}

export interface ErrorResponse {
  success: boolean;
  data: {
    statusCode: number;
    messages: string[];
  };
  timestamp: Date;
}

export interface NonDataResponse {
  success: boolean;
  timestamp: Date;
}
