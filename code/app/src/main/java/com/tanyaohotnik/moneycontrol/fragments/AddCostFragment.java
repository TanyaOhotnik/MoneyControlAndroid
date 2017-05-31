package com.tanyaohotnik.moneycontrol.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tanyaohotnik.moneycontrol.R;
import com.tanyaohotnik.moneycontrol.dao.CashTransactionDAO;
import com.tanyaohotnik.moneycontrol.dao.CategoryDAO;
import com.tanyaohotnik.moneycontrol.entities.CashTransaction;
import com.tanyaohotnik.moneycontrol.entities.Category;
import com.tanyaohotnik.moneycontrol.helpers.DateFormat;
import com.tanyaohotnik.moneycontrol.entities.OperationType;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by Tanya Ohotnik on 18.04.2017.
 */

public class AddCostFragment extends Fragment {
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_PHOTO = "DialogPhoto";
    private static final String EXTRA_DATE = "com.tanyaohotnik.moneycontrol.date";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;
    private static String EXTRA_CODE_ADD_COST = "com.tanyaohotnik.moneycontrol.cost";
    private static String EXTRA_CODE_ADD_INCOME = "com.tanyaohotnik.moneycontrol.income";
    private TextView mAddDateTextView;
    private EditText mAddSumEditText;
    private EditText mAddDescriptionEditText;
    private Button mAddButton;
    private ImageView mPhotoImageView;
    private ImageButton mAddPhotoImageButton;
    private CashTransaction mCashTransaction;
    private CashTransactionDAO mCashTransactionDAO;
    private File mPhotoFile;
    private RecyclerView mCategoryRecyclerView;
    private CategoryAdapter mAdapter;
    private int selectedPosition = -1;
    private boolean isPhotoCreated = false;

    public static Fragment newInstance() {
        AddCostFragment fragment = new AddCostFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCashTransactionDAO = new CashTransactionDAO(getActivity());
        mCashTransaction = mCashTransactionDAO.getEmptyRecord();
        if (mCashTransaction == null)
            mCashTransaction = new CashTransaction();
        // will set user id if we`ll doing back up
        // do this to get record id, so will be able to get photo file name
        long id = CashTransaction.save(mCashTransaction);
        mCashTransaction = mCashTransactionDAO.get(id);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        updateDate(new Date());
        View view = inflater.inflate(R.layout.fragment_add_transaction, container, false);
        mAddDateTextView = (TextView) view.findViewById(R.id.addDateTextView);
        mAddPhotoImageButton = (ImageButton) view.findViewById(R.id.addPhotoImageButton);
        mPhotoImageView = (ImageView) view.findViewById(R.id.transactionPhotoImageView);

        takePictureIntent();
        mAddDescriptionEditText = (EditText) view.findViewById(R.id.addDescriptionEditText);
        mAddSumEditText = (EditText) view.findViewById(R.id.addSumEditText);
        mAddDateTextView.setText(DateFormat.getNumberDateFormat(new Date()));
        mAddButton = (Button) view.findViewById(R.id.addCostButton);

        setOnClickListeners();
        setOnTextChangeListeners();
        mCategoryRecyclerView = (RecyclerView) view.findViewById(R.id.categoryListRecyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mCategoryRecyclerView.setLayoutManager(layoutManager);
        setOperationType();
        return view;
    }

    private void setOperationType() {

        boolean isCost = getActivity().getIntent().getBooleanExtra(EXTRA_CODE_ADD_COST, false),
                isIncome = getActivity().getIntent().getBooleanExtra(EXTRA_CODE_ADD_INCOME, false);
        List<Category> list = null;
        if (isCost) {
//            Toast.makeText(getActivity(),"cost",Toast.LENGTH_SHORT).show();
            mCashTransaction.setOperationType(OperationType.COST);
            list = (new CategoryDAO(getActivity())).getAllByOperationType(OperationType.COST);
        } else if (isIncome) {
            mCashTransaction.setOperationType(OperationType.INCOME);
            list = (new CategoryDAO(getActivity())).getAllByOperationType(OperationType.INCOME);

        }

        if (mAdapter == null) {
            mAdapter = new CategoryAdapter(list);
            mCategoryRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void takePictureIntent() {
        PackageManager packageManager = getActivity().getPackageManager();

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto =
                captureImage.resolveActivity(packageManager) != null;
        mAddPhotoImageButton.setEnabled(canTakePhoto);
        mPhotoFile = null;
        if (canTakePhoto) {
            mPhotoFile = mCashTransactionDAO.getPhotoFile(mCashTransaction);
        }
        if (mPhotoFile != null) {
           Uri photoURI = FileProvider.getUriForFile(getActivity(),
                    "com.tanyaohotnik.moneycontrol.fileprovider",
                    mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        }
        mAddPhotoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });
    }




    private List<Category> getCategories() {
        return (new CategoryDAO(getActivity())).getAllByOperationType(mCashTransaction.getOperationType());
    }

    private void setOnClickListeners() {
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCashTransaction.setCategoryId(mAdapter.getItemId(selectedPosition));
                if(mCashTransaction.getAmount()==0 || mCashTransaction.getCategoryId()==0){
                    Toast.makeText(getActivity(),"Не все необходимые поля заполнены!",Toast.LENGTH_SHORT).show();
                    return;}
                mCashTransactionDAO.add(mCashTransaction);
                Toast.makeText(getActivity(), R.string.recordWasAdded, Toast.LENGTH_SHORT).show();
                getActivity().finish();

            }
        });
        mAddDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
                dialog.setTargetFragment(AddCostFragment.this, REQUEST_DATE);

                dialog.show(manager, DIALOG_DATE);
            }
        });
        mPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPhotoCreated) return;
                FragmentManager manager = getFragmentManager();
                ShowPhotoFragment dialog = ShowPhotoFragment.newInstance(mPhotoFile.getPath());
                dialog.setTargetFragment(AddCostFragment.this, REQUEST_PHOTO);
                dialog.show(manager, DIALOG_PHOTO);
            }
        });
    }

    private void setOnTextChangeListeners() {
        mAddDescriptionEditText.addTextChangedListener(new TextWatcher() {
            CharSequence description = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) return;
                if (charSequence.length() == 150)
                    Toast.makeText(getActivity(), "Количество символов ограничено!", Toast.LENGTH_SHORT).show();

                description = charSequence;

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (description.length() == 0 && mCashTransaction == null) return;
                mCashTransaction.setDescription(description.toString());
            }
        });
        mAddSumEditText.addTextChangedListener(new TextWatcher() {
            int amount = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) return;
                try {
                    amount = Integer.parseInt(charSequence.toString());
                } catch (NumberFormatException nfe) {
                    Toast.makeText(getActivity(), "Неверный формат данных", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (amount == 0 && mCashTransaction == null) return;
                mCashTransaction.setAmount(amount);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(EXTRA_DATE);
            updateDate(date);
            mAddDateTextView.setText(DateFormat.getNumberDateFormat(date));
        }
        if (requestCode == REQUEST_PHOTO) {
            isPhotoCreated = true;
            setPic();
        }
    }

    private void setPic() {

        int targetW = mPhotoImageView.getWidth();
        int targetH = mPhotoImageView.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mPhotoFile.getPath(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.max(photoW / targetW, photoH / targetH) + 1;

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mPhotoFile.getPath(), bmOptions);
        mPhotoImageView.setImageBitmap(bitmap);
    }

    private void updateDate(Date date) {
        mCashTransaction.setDate(date);
    }

    /**
     * ViewHolder Class
     */
    private class CategoryHolder extends RecyclerView.ViewHolder {

        public View mIsChosenView;
        public ImageView mCategoryIconImageView;
        public TextView mCategoryNameTextView;

        public CategoryHolder(View itemView) {
            super(itemView);
            mIsChosenView = itemView.findViewById(R.id.isChosenView);
            mCategoryIconImageView = (ImageView) itemView.findViewById(R.id.categoryIconImageView);
            mCategoryNameTextView = (TextView) itemView.findViewById(R.id.categoryNameTextView);
        }

        public void bindCategory(Category category) {
            mCategoryIconImageView.setImageResource(category.getIconId());
            mCategoryNameTextView.setText(category.getName());
        }


        public void setColor(int id) {
            mIsChosenView.setBackgroundColor(getResources().getColor(id));
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {
        private List<Category> mCategoryList;


        public CategoryAdapter(List<Category> list) {
            mCategoryList = list;
        }

        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_category, parent, false);
            return new CategoryHolder(view);
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, final int position) {
            if (selectedPosition == position)
                holder.setColor(R.color.colorPrimaryDark);
            else
                holder.setColor(R.color.colorBackground);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    notifyItemChanged(selectedPosition);
                    selectedPosition = position;
                    notifyItemChanged(selectedPosition);

                }
            });
            Category category = mCategoryList.get(position);
            holder.bindCategory(category);
        }

        @Override
        public int getItemCount() {
            return mCategoryList.size();
        }

        @Override
        public long getItemId(int position) {
            return mCategoryList.get(position).getId();
        }
    }

}
