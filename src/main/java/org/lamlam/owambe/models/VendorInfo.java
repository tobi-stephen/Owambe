package org.lamlam.owambe.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author laplace zen
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorInfo {

    private Long id;
    private String instagramUrl;

    @NotNull
    private String serviceType;

    @NotNull
    private String vendorName;

    @NotNull
    private String vendorDescription;
}
