Fragment-Tab-Back-Stack
===================

Fragment Tab Back Stack manager while displaying fragments on single activity and need to maintain on back press with
multiple tabs.

Purpose
-------------

We know that there is activity stack in Android. We don't need to maintain the stack while opening or closing
activity. It will automatically handle the stack and show you the top of activity when you pressed `back` button.

But in fragment, its necessary to handle them. Because Android is not going to handle them. We need to create
a stack of fragment and manage them while pressing `back` button.

So, I have created one demo to represent how to handle the fragment in Back Stack with Tabs handling.

Usage
-------------

In the sample application, you will find one object named `fragmentStack`. Its a `Stack` which will
push and pop the fragment as per requirement.

Whenever you are displaying any new fragment, just push that fragment into stack using following code.

    //here this fragment is our first fragment
    firstListFragment = new FirstListFragment();
    firstStack.push(firstListFragment);
    
 
And when you are displaying any other fragment over this fragment, use following code.

We will create a new object of second fragment and add it to stack.

    //here this fragment is second fragment
    firstResultListFragment = new FirstResultListFragment();
    //hide the last fragment
    ft.hide(firstStack.lastElement());
    //push the new fragment into stack
    firstStack.push(firstResultListFragment);
		
![Demo](FragmentTabBackStack.gif)
	
When `backPressed` event fires, we will check whether stack size is `2` or not. If it is, then we will pop last 
fragment and display the previous fragment by following code.

    if (firstStack.size() == 2) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        firstStack.lastElement().onPause();
        ft.remove(firstStack.pop());
        firstStack.lastElement().onResume();
        ft.show(firstStack.lastElement());
        ft.commit();
    } else {
        //if size is `1` it means first fragment is visible and we can exit from application
        super.onBackPressed();
    }
    

