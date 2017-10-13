package all;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.TpFinal.data.dao.AllTestsDAO;
import com.TpFinal.data.dto.AllDTO;
import com.TpFinal.data.dto.DireccionTest;
import com.TpFinal.data.dto.LocalidadTest;
import com.TpFinal.data.dto.PersonaTest;
import com.TpFinal.services.AllTestsService;
@RunWith(Suite.class)
@SuiteClasses({ AllTestsDAO.class, AllDTO.class, AllTestsService.class})
public class AllTests {

}
