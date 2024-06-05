package com.davin.framework.tencent;

import com.davin.framework.config.CdnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author junjie.yang
 * @date 2024/1/23
 */
@Component
public class TestComand implements CommandLineRunner {
    @Autowired
    private CdnService cdnService;
    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println(cdnService);
    }
}
