package all;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.TpFinal.data.dao.AllTestsDAO;
import com.TpFinal.data.dto.AllDTO;
import com.TpFinal.services.AllTestsService;
@RunWith(Suite.class)
@SuiteClasses({ AllTestsDAO.class, AllDTO.class, AllTestsService.class})
public class AllTests {
}
