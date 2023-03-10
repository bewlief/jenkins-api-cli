package com.xtech.jenkins.client.helper;

import com.xtech.jenkins.client.model.core.JenkinsInfo;
import com.xtech.jenkins.client.model.folder.FolderJob;
import com.xtech.jenkins.client.model.job.BuildDetail;
import com.xtech.jenkins.client.model.job.Job;
import com.xtech.jenkins.client.model.job.JobDetails;
import com.xtech.jenkins.client.util.Constants;
import com.xtech.jenkins.client.util.EncodingUtils;
import com.xtech.jenkins.client.util.UrlUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * You can create, update, del a job through this manager.<br/>
 * 你可以对Jenkins的任务做创建、更新、删除的操作
 */
public class Jobs extends BaseManager {
    /**
     * create a job inside the folder
     *
     * @param folder
     * @param jobName
     * @param jobXml
     * @param crumFlag
     * @throws IOException
     */
    public void create(FolderJob folder, String jobName, String jobXml, Boolean crumFlag) throws IOException {
        //String path = UrlUtils.toBaseUrl(folder) + "createItem?name=" + EncodingUtils.encodeParam(jobName);
        String path = String.format(Constants.API_CREATE_JOB, UrlUtils.toBaseUrl(folder), EncodingUtils.encodeParam(jobName));
        getClient().postXml(path, jobXml, crumFlag);
    }

    public void create(FolderJob folderJob, String jobName, String jobXml, boolean crumFlag, boolean createFolder) throws IOException {
        if (createFolder) {
            Folders folders = new Folders();
            folders.setClient(getClient());
            boolean exists = folders.exists(folderJob.getName());

            if (!exists) {
                folders.create(folderJob.getName());
            }
        }

        create(folderJob, jobName, jobXml, crumFlag);
    }

    public void create(FolderJob folderJob, String jobName, String jobXml) throws IOException {
        String path = String.format(Constants.API_CREATE_JOB, UrlUtils.toBaseUrl(folderJob), EncodingUtils.encodeParam(jobName));

        getClient().postXml(path, jobXml);
    }

    /**
     * @param jobName
     * @param jobXml
     * @param crumFlag
     * @throws IOException
     * @see #create(FolderJob, String, String, Boolean)
     */
    public void create(String jobName, String jobXml, Boolean crumFlag) throws IOException {
        create(null, jobName, jobXml, crumFlag);
    }

    /**
     * @param jobName
     * @param jobXml
     * @throws IOException
     * @see #create(String, String, Boolean)
     */
    public void create(String jobName, String jobXml) throws IOException {
        create(jobName, jobXml, isCrumb());
    }

    /**
     * copy a job from a origin to new one
     *
     * @param originName
     * @param newName
     * @throws IOException
     */
    public void copy(String originName, String newName) throws IOException {
        getClient().post(String.format(Constants.API_COPY_JOB,
                EncodingUtils.encodeParam(originName),
                EncodingUtils.encodeParam(newName)));
    }

    /**
     * 更新流水线
     *
     * @param folderJob
     * @param jobName
     * @param jobXml
     * @param crumbFlag
     * @throws IOException
     */
    public void update(FolderJob folderJob, String jobName, String jobXml, Boolean crumbFlag) throws IOException {
        //String path = UrlUtils.toJobBaseUrl(folderJob, jobName) + "/getXml.xml";
        String path = String.format(Constants.API_UPDATE_FOLDER, UrlUtils.toJobBaseUrl(folderJob, jobName));
        getClient().postXml(path, jobXml, crumbFlag);
    }

    /**
     * @param jobName
     * @param jobXml
     * @param crumbFlag
     * @throws IOException
     * @see #update(FolderJob, String, String, Boolean)
     */
    public void update(String jobName, String jobXml, Boolean crumbFlag) throws IOException {
        update(null, jobName, jobXml, crumbFlag);
    }

    /**
     * @param jobName
     * @param jobXml
     * @throws IOException
     * @see #update(String, String, Boolean)
     */
    public void update(String jobName, String jobXml) throws IOException {
        update(jobName, jobXml, isCrumb());
    }

    /**
     * delete a job
     *
     * @param folderJob
     * @param jobName
     * @param crumbFlag
     */
    public void delete(FolderJob folderJob, String jobName, Boolean crumbFlag) throws IOException {
        //String path = UrlUtils.toJobBaseUrl(folderJob, jobName) + "/doDelete";
        String path = String.format(Constants.API_DELETE_FOLDER_JOB, UrlUtils.toJobBaseUrl(folderJob, jobName));
        getClient().post(path, crumbFlag);
    }

    /**
     * @param jobName
     * @param crumbFlag
     * @throws IOException
     * @see #delete(FolderJob, String, Boolean)
     */
    public void delete(String jobName, Boolean crumbFlag) throws IOException {
        delete(null, jobName, crumbFlag);
    }

    /**
     * @param jobName
     * @throws IOException
     * @see #delete(String, Boolean)
     */
    public void delete(String jobName) throws IOException {
        delete(jobName, isCrumb());
    }

    /**
     * 批量删除任务（job）
     *
     * @param prefixName
     * @return
     * @throws IOException
     */
    public int batchDel(String prefixName) throws IOException {
        List<Job> allJobs = getAllJobs();
        int count = 0;
        if (allJobs == null) {
            return count;
        }

        for (Job job : allJobs) {
            if (job.getName().startsWith(prefixName)) {
                delete(job.getName());
                count++;
            }
        }

        return count;
    }

    /**
     * 重命名job名称
     *
     * @param folderJob
     * @param oldName
     * @param newName
     * @param crumbFlag
     * @throws IOException
     */
    public void rename(FolderJob folderJob, String oldName, String newName, Boolean crumbFlag)
            throws IOException {
        //String path = UrlUtils.toJobBaseUrl(folderJob, oldName) + "/doRename?newName=" + EncodingUtils.encodeParam(newName);
        String path = String.format(Constants.API_RENAME_FOLDER_JOB, UrlUtils.toJobBaseUrl(folderJob, oldName), EncodingUtils.encodeParam(newName));
        getClient().post(path, crumbFlag);
    }

    /**
     * @param oldName
     * @param newName
     * @param crumbFlag
     * @throws IOException
     * @see #rename(FolderJob, String, String, Boolean)
     */
    public void rename(String oldName, String newName, Boolean crumbFlag) throws IOException {
        rename(null, oldName, newName, crumbFlag);
    }

    /**
     * @param oldName
     * @param newName
     * @throws IOException
     * @see #rename(String, String, Boolean)
     */
    public void rename(String oldName, String newName) throws IOException {
        rename(oldName, newName, isCrumb());
    }

    /**
     * 获取job的xml配置内容
     *
     * @param folderJob
     * @param jobName
     * @return
     * @throws IOException
     */
    public String getXml(FolderJob folderJob, String jobName) throws IOException {
        String path = String.format(Constants.API_GET_JOB_XML, UrlUtils.toJobBaseUrl(folderJob, jobName));
        return getClient().get(path);
    }

    /**
     * @param jobName
     * @return
     * @throws IOException
     * @see #getXml(FolderJob, String)
     */
    public String getXml(String jobName) throws IOException {
        return getXml(null, jobName);
    }

    /**
     * disable a job by jobName<br/>
     * 禁用
     *
     * @param jobName
     * @param crumbFlag
     * @throws IOException
     */
    public void disable(String jobName, Boolean crumbFlag) throws IOException {
        getClient().post(String.format(Constants.API_DISABLE_JOB, EncodingUtils.encode(jobName)), isCrumb());
    }

    /**
     * @param jobName
     * @throws IOException
     * @see #disable(String, Boolean)
     */
    public void disable(String jobName) throws IOException {
        disable(jobName, isCrumb());
    }

    /**
     * 启用
     *
     * @param jobName
     * @param crumbFlag
     * @throws IOException
     */
    public void enable(String jobName, Boolean crumbFlag) throws IOException {
        getClient().post(String.format(Constants.API_ENABLE_JOB, EncodingUtils.encode(jobName)), isCrumb());
    }

    /**
     * @param jobName
     * @throws IOException
     * @see #enable(String, Boolean)
     */
    public void enable(String jobName) throws IOException {
        enable(jobName, isCrumb());
    }

    /**
     * Build a job by name
     *
     * @param jobName
     * @throws IOException
     */
    public void build(String jobName) throws IOException {
        getClient().post(String.format(Constants.API_BUILD_JOB, EncodingUtils.encode(jobName)), isCrumb());
    }

    public void build(String folder, String jobName, String branch) throws IOException {
        //getClient().post("/job/" + EncodingUtils.encode(folder)
        //        + "/job/" + EncodingUtils.encode(jobName)
        //        + "/job/" + EncodingUtils.encode(branch) + "/build", isCrumb());

        getClient().post(String.format(Constants.API_BUILD_FOLDER_JOB,
                EncodingUtils.encode(folder),
                EncodingUtils.encode(jobName),
                EncodingUtils.encode(branch)), isCrumb());
    }

    /**
     * Build a job with params<br/>
     * 构建参数化的任务
     *
     * @param jobName job name
     * @param params  param map
     * @throws IOException
     */
    public void buildWithParams(String jobName, Map<String, String> params) throws IOException {
        String path = String.format(Constants.API_BUILD_WITH_PARAMS, EncodingUtils.encode(jobName));
        StringBuilder urlBuf = new StringBuilder(path);

        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = params.get(key);
            urlBuf.append("&").append(key).append("=").append(value);
        }

        getClient().post(urlBuf.toString(), isCrumb());
    }

    /**
     * Get log text from a job and buildNum
     *
     * @param jobName
     * @param buildNum
     * @return
     * @throws IOException
     */
    public String getLogText(String jobName, int buildNum) throws IOException {
        return getClient().get(String.format(Constants.API_GET_LOG_TEXT, EncodingUtils.encode(jobName), buildNum));
        // xjm: /consoleText=/logText/progressiveText
    }

    public String getLogHtml(String jobName, int buildNum) throws IOException {
        String path = String.format(Constants.API_GET_LOG_HTML, EncodingUtils.encode(jobName), buildNum);
        return getClient().get(path);
    }

    /**
     * stop the job by name
     *
     * @param jobName
     * @param buildId
     * @throws IOException
     */
    public void stop(String jobName, int buildId) throws IOException {
        String path = String.format(Constants.API_STOP_JOB, EncodingUtils.encode(jobName), buildId);
        getClient().post(path, isCrumb());
    }

    public JenkinsInfo getAll() throws IOException {
        return getClient().get("/", JenkinsInfo.class);
    }

    /**
     * 获取所有的任务（job）
     *
     * @return
     * @throws IOException
     */
    public List<Job> getAllJobs() throws IOException {
        JenkinsInfo jenkinsInfo = getAll();
        return jenkinsInfo.getJobs();
    }

    public JobDetails getDetails(String jobName) throws IOException {
        String path = String.format(Constants.API_GET_JOB_DETAILS, EncodingUtils.encode(jobName));
        return getClient().get(path, JobDetails.class);
    }

    /**
     * 获取某一次构建的详细信息
     *
     * @param jobName     任务名称
     * @param buildNumber 构建号
     * @return 构建详细信息
     * @throws IOException
     */
    public BuildDetail getBuildDetails(String jobName, int buildNumber) throws IOException {
        String path = String.format(Constants.API_GET_BUILD_DETAILS, EncodingUtils.encode(jobName), buildNumber);
        return getClient().get(path, BuildDetail.class);
    }

    /**
     * 获取最后一次的构建信息
     *
     * @param jobName
     * @return
     * @throws IOException
     */
    public BuildDetail getLastBuildDetails(String jobName) throws IOException {
        JobDetails jobDetails = getDetails(jobName);
        int lastBuildNum = jobDetails.getLastBuild().getNumber();

        return getBuildDetails(jobName, lastBuildNum);
    }

    @Override
    protected String[] getDependencyArray() {
        return new String[0];
    }
}
