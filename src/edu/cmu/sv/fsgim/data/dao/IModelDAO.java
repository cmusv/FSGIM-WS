package edu.cmu.sv.fsgim.data.dao;

import java.util.List;

import edu.cmu.sv.fsgim.data.po.BasePO;
import edu.cmu.sv.fsgim.data.po.ModelPO;

public interface IModelDAO<PO extends BasePO> extends IBaseDAO<PO>  {
	List<ModelPO> findByName(String modelName);
}
