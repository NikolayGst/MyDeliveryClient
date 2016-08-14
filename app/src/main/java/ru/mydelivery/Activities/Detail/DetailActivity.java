package ru.mydelivery.Activities.Detail;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.mydelivery.Activities.Detail.Presenter.DetailPresenter;
import ru.mydelivery.Activities.Detail.Presenter.DetailPresenterImpl;
import ru.mydelivery.Activities.Detail.View.DetailView;
import ru.mydelivery.R;
import ru.mydelivery.network.Model.Detail.JobForUser;
import ru.mydelivery.network.Model.Detail.User;


public class DetailActivity extends AppCompatActivity implements DetailView<JobForUser> {

    @BindView(R.id.txtPrice)
    TextView mTxtPrice;
    @BindView(R.id.txtFio)
    TextView mTxtFio;
    @BindView(R.id.txtTelephone)
    TextView mTxtTelephone;
    @BindView(R.id.txtPick)
    TextView mTxtPick;
    @BindView(R.id.txtAddress)
    TextView mTxtAddress;
    @BindView(R.id.txtDate)
    TextView mTxtDate;
    @BindView(R.id.txtDesc)
    TextView mTxtDesc;
    @BindView(R.id.txtNote)
    TextView mTxtNote;

    private Bundle intent;
    private String mUserId;
    private String mJobsId;

    private DetailPresenter mDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        init();

        mDetailPresenter = new DetailPresenterImpl(this);
        mDetailPresenter.loadJob(mUserId, mJobsId);

    }

    private void init() {
        intent = getIntent().getExtras();
        mUserId = intent.getString("userId");
        mJobsId = intent.getString("jobsId");
    }

    @OnClick(R.id.btnStatus)
    void showStatusDialog() {
        mDetailPresenter.showStatusDialog(mUserId, mJobsId, getSupportFragmentManager());
    }

    @Override
    public void onJobLoaded(JobForUser jobForUser) {
        User user = jobForUser.getUser();
        mTxtFio.setText(user.getFio());
        mTxtTelephone.setText(user.getTelephone());
        mTxtPick.setText(user.getPickFrom());
        mTxtAddress.setText(user.getAddress());
        mTxtDate.setText(user.getDate());
        mTxtDesc.setText(user.getDescription());
        mTxtNote.setText(user.getNote());
        mTxtPrice.setText(user.getPrice() + " грн.");
    }

    @Override
    public void errorIsServer(@StringRes int resId) {
        Toast.makeText(DetailActivity.this, resId, Toast.LENGTH_SHORT).show();
    }
}