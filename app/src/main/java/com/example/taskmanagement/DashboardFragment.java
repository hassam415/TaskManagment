package com.example.taskmanagement;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentDashboardBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment  {
    FragmentDashboardBinding  fragmentDashboardBinding;
ActionBarDrawerToggle toggle;
NavigationView navigation;
DashboardAdapter dashboardAdapter;
List<Status>statusList=new ArrayList<>();
DashboardViewModel dashboardViewModel;
DeadlineListAdapter deadlineListAdapter;
WorkLoadAdapter workLoadAdapter;
List<TeamWorkLoad>teamWorkLoadList=new ArrayList<>();
List<UpcomingDeadLine>upcomingDeadLines=new ArrayList<>();
ReviewAdapter reviewAdapter;
List<Review>reviewList=new ArrayList<>();
StatusViewModel statusViewModel;
    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDashboardBinding=FragmentDashboardBinding.inflate(inflater,container,false);

        ImageView filterBtn=requireActivity().findViewById(R.id.filterBtn);
//fragmentDashboardBinding.dashboardrecyler.setHasFixedSize(true);
//fragmentDashboardBinding.dashboardrecyler.setLayoutManager(new GridLayoutManager(getContext(),2));
//dashboardAdapter=new DashboardAdapter(getContext(),statusList);
//fragmentDashboardBinding.dashboardrecyler.setAdapter(dashboardAdapter);
//statusViewModel=new StatusViewModel(new StatusRepository());
//filterBtn.setVisibility(View.GONE);
       dashboardViewModel=new DashboardViewModel(new DashboardRepository());
       fragmentDashboardBinding.deadlineList.setHasFixedSize(true);
       fragmentDashboardBinding.deadlineList.setLayoutManager(new LinearLayoutManager(getContext()));
        deadlineListAdapter=new DeadlineListAdapter(getContext(),upcomingDeadLines);
        fragmentDashboardBinding.deadlineList.setAdapter(deadlineListAdapter);
        fragmentDashboardBinding.teamrecyler.setHasFixedSize(true);
fragmentDashboardBinding.teamrecyler.setLayoutManager(new LinearLayoutManager(getContext()));
workLoadAdapter=new WorkLoadAdapter(getContext(),teamWorkLoadList);
fragmentDashboardBinding.teamrecyler.setAdapter(workLoadAdapter);
fragmentDashboardBinding.reviewrecycler.setHasFixedSize(true);
fragmentDashboardBinding.reviewrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewAdapter=new ReviewAdapter(getContext(),reviewList);
fragmentDashboardBinding.reviewrecycler.setAdapter(reviewAdapter);

    //   dashboardViewModel.getTotalTask(new ResponseCallback<Integer>() {
      //     @Override
        //   public void onSuccess(Integer data, String message) {
            //   fragmentDashboardBinding.taskcount.setText(String.valueOf(data));
         //  }

           //@Override
         //  public void onError(String message) {

         //  }
     //  });
      // dashboardViewModel.getTotalUser(new ResponseCallback<Integer>() {
        //   @Override
          // public void onSuccess(Integer data, String message) {
              // fragmentDashboardBinding.usercount.setText(String.valueOf(data));
          // }

         //  @Override
         //  public void onError(String message) {

         //  }
      // });


      //  statusViewModel.getList(new ResponseCallback<List<Status>>() {
        //    @Override
          //  public void onSuccess(List<Status> data, String message) {

            //    statusList.clear();
              //  statusList.addAll(data);
                //dashboardAdapter.notifyDataSetChanged();

               // for (Status status : data) {

               //     dashboardViewModel.getStatus(
                 //           status.getId(),
                   //         new ResponseCallback<Integer>() {
                     //           @Override
                       //         public void onSuccess(Integer count, String message) {

                         //           dashboardAdapter.setTaskCount(status.getId(), count);
                           //     }

                             //   @Override
                               // public void onError(String message) {

                             //   }
                          //  }
                 //   );
              //  }
            //}

       //     @Override
         //   public void onError(String message) {
           // }
       // });


dashboardViewModel.getTaskByApi(new ResponseCallback<ResponseDashBoard>() {
    @Override
    public void onSuccess(ResponseDashBoard data, String message) {
        fragmentDashboardBinding.completionRate.setText(data.getData().getDashboardOverView().completion_rate + "%");
        fragmentDashboardBinding.taskCount.setText(data.getData().getDashboardOverView().getTotaltask());
        fragmentDashboardBinding.pendingCount.setText(data.getData().getDashboardOverView().getInreview());
        fragmentDashboardBinding.completCount.setText(data.getData().getDashboardOverView().getComplete());
        fragmentDashboardBinding.dueCount.setText(data.getData().getDashboardOverView().getOverDue());
        fragmentDashboardBinding.draftCount.setText(data.getData().getStatusDistribution().getDraft().count);
        fragmentDashboardBinding.assignedCount.setText(data.getData().getStatusDistribution().getAssigned().count);
        fragmentDashboardBinding.inProgressCount.setText(data.getData().getStatusDistribution().getInProgress().count);
        fragmentDashboardBinding.onHoldCount.setText(data.getData().getStatusDistribution().getOnHold().count);
        fragmentDashboardBinding.inReviewCount.setText(data.getData().getStatusDistribution().getInReview().count);
        fragmentDashboardBinding.changesRequestedCount.setText(data.getData().getStatusDistribution().getChangesRequested().count);
        fragmentDashboardBinding.completedStatusCount.setText(data.getData().getStatusDistribution().getCompleted().count);
        fragmentDashboardBinding.cancelledCount.setText(data.getData().getStatusDistribution().getCancelled().count);
        fragmentDashboardBinding.lowTaskCount.setText(data.getData().getPriorityDistribution().getLow().count + "tasks");
        fragmentDashboardBinding.normaltaskcount.setText(data.getData().getPriorityDistribution().getNormal().count + "tasks");
        fragmentDashboardBinding.highcount.setText(data.getData().getPriorityDistribution().getHigh().count + "tasks");
        fragmentDashboardBinding.urgentcount.setText(data.getData().getPriorityDistribution().getUrgent().count + "tasks");
        fragmentDashboardBinding.pendingreview.setText(data.getData().getDashboardOverView().getInreview()+"pending");
int totaltask= Integer.parseInt(data.getData().getDashboardOverView().getTotaltask());
        Priority_Distribution priority = data.getData().priorityDistribution;

        setPriorityProgress(priority, totaltask);
       List<UpcomingDeadLine>deadLines=data.getData().getUpcomingDeadLine();
       upcomingDeadLines.addAll(deadLines);
       deadlineListAdapter.notifyDataSetChanged();
       List<TeamWorkLoad> teamWorkLoad=data.getData().getTeamWorkLoad();
       teamWorkLoadList.addAll(teamWorkLoad);
       workLoadAdapter.notifyDataSetChanged();
       List<Review>reviews=data.getData().getReviews();
       reviewList.addAll(reviews);
       reviewAdapter.notifyDataSetChanged();
    }
    @Override
    public void onError(String message) {

    }

});


        return fragmentDashboardBinding.getRoot();

    }

        private void setPriorityProgress(Priority_Distribution priority,int totaltask) {

            int low = Integer.parseInt(priority.getLow().count);
            int normal = Integer.parseInt(priority.getNormal().count);
            int high = Integer.parseInt(priority.getHigh().count);
            int urgent = Integer.parseInt(priority.getUrgent().count);

            fragmentDashboardBinding.lowPriorityProgress.setMax(100);
            fragmentDashboardBinding.normalpriority.setMax(100);
            fragmentDashboardBinding.highprioirty.setMax(100);
            fragmentDashboardBinding.urgentprogress.setMax(100);

            fragmentDashboardBinding.lowPriorityProgress.setProgress(
                    totaltask > 0 ? low * 100 / totaltask : 0);

            fragmentDashboardBinding.normalpriority.setProgress(
                    totaltask > 0 ? normal * 100 / totaltask : 0);

            fragmentDashboardBinding.highprioirty.setProgress(
                    totaltask > 0 ? high * 100 / totaltask : 0);

            fragmentDashboardBinding.urgentprogress.setProgress(
                    totaltask > 0 ? urgent * 100 / totaltask : 0);

            fragmentDashboardBinding.lowTaskCount.setText(low + " tasks");
            fragmentDashboardBinding.normaltaskcount.setText(normal + " tasks");
            fragmentDashboardBinding.highcount.setText(high + " tasks");
            fragmentDashboardBinding.urgentcount.setText(urgent + " tasks");
        }
}