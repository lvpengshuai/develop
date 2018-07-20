import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by panyan on 2017/2/23.
 */
@ContextConfiguration(locations={"classpath:/spring/spring-dao.xml"})
@Service
public class TestService extends TestCase {


    @Test
    public void testKnowledgeElement(){

    }

}
