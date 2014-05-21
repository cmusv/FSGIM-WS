package edu.cmu.sv.fsgim.data.dao;

import java.util.List;

import edu.cmu.sv.fsgim.data.po.BasePO;
import edu.cmu.sv.fsgim.data.po.VersionPO;

public interface IVersionDAO<PO extends BasePO> extends IBaseDAO<PO> {
	List<VersionPO> findByVersionNumber(String modelName, String versionNumber);
}
