package dev.bujiku.springbootjooqskeleton;

import at.favre.lib.bytes.Bytes;
import at.favre.lib.idmask.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tz.co.vodacom.security.oauth2authorizationserver.config.IdUnMaskException;

/**
 * A component used to mask and unmask IDs for better security
 *
 * @author Newton Bujiku
 * @since 2024
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class IdMasker {
    private final IdMask<Long> idMask;

    public IdMasker() {
        String arr = Bytes.random(16).encodeHex();
        byte[] idMaskKey = Bytes.parseHex(arr).array();
        idMask = IdMasks.forLongIds(Config.builder(idMaskKey)
                //.randomizedIds(true)
                .encoding(new ByteToTextEncoding.Base16())
                .enableCache(true)
                //.autoWipeMemory()
                //.cacheImpl()
                .build());
    }

    public String mask(Long id) {
        return idMask.mask(id);
    }

//    public <T extends BaseDTO> T mask(T dto) {
//        var stringId = dto.getId();
//        dto.setId(idMask.mask(Long.valueOf(stringId)));
//        return dto;
//    }

    public Long unmask(String id) {
        try {
            return idMask.unmask(id);
        } catch (IdMaskSecurityException e) {
            log.error("AN ERROR OCCURED WHILE UNMASKING ID: {}", id, e);
        }
        throw new IdUnMaskException();
    }
}
