package io.hexbit.core.common.response;

import io.hexbit.core.common.utils.Paging;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.*;

@Data
public class CustomResponse {

    private Map<String, Object> status = new LinkedHashMap<>();
    private Map<String, Object> data;

    public CustomResponse(Builder builder) {
        this.status.put("number", builder.responseCodes.getNumber());
        this.status.put("code", builder.responseCodes.getCode());
        this.status.put("message", builder.responseCodes.getMessage());
        this.data = builder.data;
    }

    public static final class Builder {

        private final CustomResponseCodes responseCodes;
        private final Map<String, Object> data;

        public Builder() {
            this(CustomCommonResponseCodes.OK);
        }

        public Builder(CustomResponseCodes responseCodes) {
            this.data = new LinkedHashMap<>();
            this.responseCodes = responseCodes;
        }

        public Builder addItems(Object item) {
            this.data.put("items", Objects.nonNull(item) ? List.of(item) : new ArrayList<>());
            return this;
        }

        public Builder addItems(Collection<?> items) {
            this.data.put("items", Objects.nonNull(items) ? items : new ArrayList<>());
            return this;
        }

        public Builder addItems(Page<?> items) {
            this.data.put("items", Objects.nonNull(items) ? items.getContent() : new ArrayList<>());
            this.data.put("paging", Objects.nonNull(items) ? Paging.of(items) : null);
            return this;
        }

        public Builder addData(String k1, Object v1) {
            this.data.put(k1, v1);
            return this;
        }

        public Builder addData(String k1, Object v1, String k2, Object v2) {
            this.data.put(k1, v1);
            this.data.put(k2, v2);
            return this;
        }

        public Builder addData(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
            this.data.put(k1, v1);
            this.data.put(k2, v2);
            this.data.put(k3, v3);
            return this;
        }

        public CustomResponse build() {
            return new CustomResponse(this);
        }

    }


}

