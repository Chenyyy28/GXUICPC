package manage.mail.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelDataListener implements ReadListener {
    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        log.info(o.toString());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
