package code.kliangh.system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SystemInfoTest {
    private SystemInfo systemInfo;

    @BeforeEach
    void setUp() {
        systemInfo = new SystemInfo();
    }

    @Test
    void health() {
        systemInfo.health();
    }

}