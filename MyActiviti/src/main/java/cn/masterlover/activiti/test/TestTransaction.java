package cn.masterlover.activiti.test;

import static com.gzbkfpt.jtkafpt.modules.flowable.test.AbstractTestCase.assertEquals;
import static com.gzbkfpt.jtkafpt.modules.flowable.test.AbstractTestCase.fail;

import com.gzbkfpt.jtkafpt.config.flowable.response.ResponseResult;
import com.gzbkfpt.jtkafpt.config.flowable.response.ResultUtil;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试事务
 *
 * @author Master
 * @since 2020/4/29 15:23
 */
@Slf4j
@RestController
@RequestMapping(value = "/flowable/test")
//@Transactional(rollbackFor = Exception.class)
public class TestTransaction {


  @Autowired
  private RuntimeService runtimeService;

  @GetMapping(value = "/startByKey/{key}")
  @ApiOperation(value = "测试发起")
  public ResponseResult<Object> TestStart(@PathVariable String key) {
    try {
      Map<String, Object> map = new HashMap<>();
      map.put("user0", "admin");
      ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, map);

      fail("Starting the process instance should throw an exception");
      return ResultUtil.data(processInstance, "");
    } catch (Exception e) {
      assertEquals("Buzzz", e.getMessage());
      e.printStackTrace();
    }
    assertEquals(0, runtimeService.createExecutionQuery().count());
    return ResultUtil.error("");
  }
}
