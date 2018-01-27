package bpm.debops;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/snapshot")
public class BpmSnapshotDeployControl {

@RequestMapping(value="/snapshot/create/{name}", method= RequestMethod.GET)	
public ReturnInfo createSnapshot(@PathVariable("name") String buildName, 
		@RequestParam("processName") String processName, 
		@RequestParam("trackName") String trackName)
{

	return runCreateSnapshotCommand(processName, trackName,buildName);
}
@RequestMapping(value="/snapshot/ archive/{snapshotId}", method= RequestMethod.GET)	
public ReturnInfo archiveSnapshot(@PathVariable("snapshotId") String snapshotId, 
		@RequestParam("processName") String processName, 
		@RequestParam("trackName") String trackName)
{

	return runArchiveSnapshotCommand(processName, trackName,snapshotId);
}

private ReturnInfo runCreateSnapshotCommand(String strProcessName,String trackName,String buildName)
{
	ReturnInfo r = new ReturnInfo();
	String snaphotName ="B";
	String snaphotId ="B";
	SimpleDateFormat sd = new SimpleDateFormat("HHmmssS");
	String strTimestamp = sd.format(new Date());
	snaphotName = snaphotName+strTimestamp;
	snaphotId =snaphotId+strTimestamp.substring(0, 6);
	r.setSnapShotId(snaphotId);
	String command ="C:/IBM/WebSphere/AppServer/profiles/Node1Profile/bin/wsadmin.bat -conntype SOAP -port 8880 -host sripramatta-PC -user admin -password admin -lang jython -f C:/IBM/WebSphere/AppServer/profiles/Node1Profile/bin/devops.py 1 %s %s %s %s %s";
	command = String.format(command, strProcessName,snaphotName,trackName,buildName,snaphotId);
	StringBuffer output =new StringBuffer("");

	try
	{
		Process p = Runtime.getRuntime().exec(command);
		p.waitFor();
		r.setReturnValue(p.exitValue());
		BufferedReader reader =  new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
		while ((line = reader.readLine())!= null) {
			output.append(line + "\n");
		}
		r.setMsg(output.toString());
		
	}
	catch(Exception e)
	{
		r.setMsg(e.getMessage());
		r.setReturnValue(-1);
	}
	return r;
}
private ReturnInfo runArchiveSnapshotCommand(String strProcessName,String trackName,String snapshotId)
{
	ReturnInfo r = new ReturnInfo();
	String snaphotName ="B";
	String snaphotId ="B";
	SimpleDateFormat sd = new SimpleDateFormat("HHmmssS");
	String strTimestamp = sd.format(new Date());
	snaphotName = snaphotName+strTimestamp;
	snaphotId =snaphotId+strTimestamp.substring(0, 6);
	r.setSnapShotId(snaphotId);
	String command ="C:/IBM/WebSphere/AppServer/profiles/Node1Profile/bin/wsadmin.bat -conntype SOAP -port 8880 -host sripramatta-PC -user admin -password admin -lang jython -f C:/IBM/WebSphere/AppServer/profiles/Node1Profile/bin/devops.py 2 ? ? ?";
	command = String.format(command, strProcessName,trackName,snaphotId);
	StringBuffer output =new StringBuffer("");

	try
	{
		Process p = Runtime.getRuntime().exec(command);
		p.waitFor();
		r.setReturnValue(p.exitValue());
		BufferedReader reader =  new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
		while ((line = reader.readLine())!= null) {
			output.append(line + "\n");
		}
		r.setMsg(output.toString());
		
	}
	catch(Exception e)
	{
		r.setMsg(e.getMessage());
		r.setReturnValue(-1);
	}
	return r;
}

}
