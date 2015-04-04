package jobs;

import controllers.Dev;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import utils.DatabaseUtil;

@OnApplicationStart
public class Bootstrap extends Job{
	public void doJob() {
		DatabaseUtil.init();
     //   DatabaseUtil.checkOSS();
    }
}
