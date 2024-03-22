package dev.bujiku.springbootjooqskeleton;

import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Newton Bujiku
 * @since 2024
 */

public class JooqTests extends SpringBootJooqSkeletonApplicationTests{
    @Autowired
    DSLContext dslContext;

    @Test
    @Order(0)
    @DisplayName("Select One")
    public void selectOne(){
        var one = dslContext.selectOne()
                .fetch()
                .into(Integer.class);
        assertThat(one.size()).isEqualTo(1);
        assertThat(one.get(0)).isEqualTo(1);
    }
}
