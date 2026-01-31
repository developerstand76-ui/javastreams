package com.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Stream operation result")
public class StreamResult {
    
    @Schema(description = "Execution approach used", example = "Stream API")
    private String approach;
    
    @Schema(description = "Result data")
    private Object result;
    
    @Schema(description = "Execution time in milliseconds", example = "5")
    private long executionTimeMs;

    public StreamResult() {
    }

    public StreamResult(String approach, Object result, long executionTimeMs) {
        this.approach = approach;
        this.result = result;
        this.executionTimeMs = executionTimeMs;
    }

    public String getApproach() {
        return approach;
    }

    public void setApproach(String approach) {
        this.approach = approach;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }
}
