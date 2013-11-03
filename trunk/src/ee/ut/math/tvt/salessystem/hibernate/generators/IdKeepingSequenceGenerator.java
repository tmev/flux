package ee.ut.math.tvt.salessystem.hibernate.generators;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentityGenerator;
import ee.ut.math.tvt.salessystem.domain.data.DisplayableItem;

public class IdKeepingSequenceGenerator extends IdentityGenerator{
	@Override
	   public Serializable generate(SessionImplementor session, Object object)
	         throws HibernateException {
	      if (object instanceof DisplayableItem) {
	         DisplayableItem persistent = (DisplayableItem)object;
	         if (persistent.getId() != null && persistent.getId() > 0) {
	            return persistent.getId();
	         }
	      }
	      return super.generate(session, object);
	   }
}
