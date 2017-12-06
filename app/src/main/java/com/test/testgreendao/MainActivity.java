package com.test.testgreendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.test.testgreendao.greendao.DaoMaster;
import com.test.testgreendao.greendao.DaoSession;
import com.test.testgreendao.greendao.StudentInfoDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DaoMaster daoMaster;
    DaoSession daoSession;
    StudentInfoDao studentInfoDao;

    Button add, del, update, check;

    ListView lvList;
    private List<StudentInfo> studentInfoList;
    List<String> mListData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGreenDao();
        lvList=findViewById(R.id.lvList);
        add = findViewById(R.id.add);
        add.setOnClickListener(this);
        del = findViewById(R.id.del);
        del.setOnClickListener(this);
        update = findViewById(R.id.update);
        update.setOnClickListener(this);
        check = findViewById(R.id.check);
        check.setOnClickListener(this);

    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "test.db", null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        studentInfoDao = daoSession.getStudentInfoDao();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                addDataForOne();
//                addDataMany();
                checkAllData();
                setDataForList(mListData);
                break;
            case R.id.del:
//                delDataForOne();
                delAll();
                mListData.clear();
                checkAllData();
                setDataForList(mListData);
                break;
            case R.id.update:
                updateData();
                checkAllData();
                setDataForList(mListData);
                break;
            case R.id.check:
//                checkDataForSome();
                checkAllData();
                setDataForList(mListData);
                break;
            default:
                break;
        }
    }

    /**
     * 增加一条数据
     */
    private void addDataForOne() {
        StudentInfo studentInfo = new StudentInfo();
        // 写入数据
        studentInfo.setId(null);
        studentInfo.setName("张三");
        studentInfo.setGrade("三年级");
        studentInfo.setScore(80);
        studentInfoDao.insert(studentInfo);
    }

    /**
     * 增加多条数据
     */
    private void addDataMany() {
        //两个方法本质是一样的，只是插入的时间顺序有所区别

        //方法一：循环插入数据
//        for (int i = 0; i < 3; i++) {
//            StudentInfo studentInfo = new StudentInfo();
//            studentInfo.setId(null);
//            studentInfo.setName("张三" + i);
//            studentInfo.setGrade("三年级");
//            studentInfo.setScore(80 + i);
//            studentInfoDao.insert(studentInfo);
//        }

        //方法二:一次插入多条数据
        List<StudentInfo> mList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setId(null);
            studentInfo.setName("李四" + i);
            studentInfo.setGrade("三年级");
            studentInfo.setScore(80 + i);
            mList.add(studentInfo);
        }
        studentInfoDao.insertInTx(mList);
    }

    /**
     * 删除指定信息
     */
    private void delDataForOne() {
        //删除名字为李四1的学生
        studentInfoDao.queryBuilder().where(StudentInfoDao.Properties.Name.eq("李四1")).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    /**
     * 删除所有数据
     */

    private void delAll() {
        studentInfoDao.deleteAll();
    }

    /**
     * 更新指定数据
     */
    private void updateData() {
        StudentInfo studentInfo = studentInfoDao.queryBuilder().where(StudentInfoDao.Properties.Id.eq(2)).build()
                .unique();
        if (studentInfo != null) {
            studentInfo.setName("王五");
        }
    }

    /**
     * 查询所有数据
     */
    private void checkAllData() {
        studentInfoList = studentInfoDao.queryBuilder().list();
    }

    /**
     * 查询指定数据
     */

    private void checkDataForSome(){
        studentInfoList=studentInfoDao.queryBuilder().where(StudentInfoDao.Properties.Score.eq(80)).list();
    }

    private void setDataForList(List<String> dataForList) {
        for (int i = 0; i < studentInfoList.size(); i++) {
            String data = "id:" + studentInfoList.get(i).getId() + ",name:" + studentInfoList.get(i).getName()
                    + ",Grade:" + studentInfoList.get(i).getGrade() + ",Score:" + studentInfoList.get(i).getScore();
            mListData.add(data);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, dataForList);
        lvList.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }
}
