# across we have name (20 of them)                                                                                          (20)

# now sword enchants: champion 10 (1), cleave 6 (1), critical 6-7 (2), cubism 6 (1), divine_gift 1-3 (mergable 1),          (6)
# dragon_hunter 1-5 (mergable 1), ender_slayer 6-7 (2), execute 6 (1), experience 4-5 (2), fire_aspect 3 (1),               (7)
# first_strike 5 (1), giant_killer 6-7 (2), lethality 6 (1), life_steal 4-5 (2), looting 4-5 (2), luck 6-7 (2)              (10)
# continuing: prosecute 6 (1), scavenger 4-5 (2), sharpness 6-7 (2), smite 6-7 (2), syphon 4-5 (2), thunderbolt 6 (1),      (10)
# thunderlord 6-7 (2), titan_killer 6-7 (2), triple_strike 5 (1), vampirism 6 (1), venemous 6 (1), vicious 3, 5 (2)         (9)
# ultimates: ultimate_chimera 1-5 (mergable 1), ultimate_combo 1-5 (mergable 1), ultimate_fatal_tempo 1-5 (mergable 1),     (8)
# ultimate_habanero_tactics 4-5 (mergable 1), utlimate_inferno 1-5 (mergable 1), ultimate_one_for_all 1 (mergable 1),
# ultimate_swarm 1-5(mergable 1), ultimate_soul_eater 1-5 (mergable 1)

# now armor enchants: big_brain 3-5 (mergable 1), counter_strike 5 (1), ferocious_mana 1-10 (mergable 1), growth 6-7 (2),            (5)
# hardened_mana 1-10 (mergable 1), hecatomb 1-10 (mergable 1), mana_vampire 1-10 (1),                                       (3)
# pesterminator 1-5 (1), protection 6-7 (2), refrigerate 1-5 (1), rejuvenate 1-5 (1), smarty_pants 1-5 (1),                 (6)
# strong_mana 1-10 (1), sugar_rush 1-3 (1), transylvanian 4-5 (1), true_protection 1 (1)                                    (4)
# ultimates: ultimate_bank 1-5 (mergable 1), ultimate_last_stand 1-5 (1), ultimate_legion 1-5 (1),                          (3)
# ultimate_wise 1-5 (1), ultimate_wisdom 1-5 (1)                                                                            (2)

# sword reforges: suspicious, fabled, withered                                                                              (3)
# armor reforges: giant, necrotic, loving, ancient                                                                          (4)
#                                                                                                                           at 100
# wither essence + master stars                                                                                             (40)
# numfpbs 0-5                                                                                                               (1)
# recomb                                                                                                                    (1)
#                                                                                                                           (142)

import csv

import numpy as np
import pandas as pd
import statsmodels.api as sm

pd.set_option('display.max_rows', 500)
pd.set_option('display.max_columns', 500)
pd.set_option('display.width', 1000)

single_features = {'champion': [10], 'cleave': [6], 'cubism': [6],  #
                   'execute': [6], 'fire_aspect': [3],  #
                   'first_strike': [5], 'lethality': [6],  #
                   'prosecute': [6], 'thunderbolt': [6],  #
                   'triple_strike': [5], 'vampirism': [6], 'venemous': [6],  #
                   'ultimate_one_for_all': [1],  #
                   #
                   ###
                   'counter_strike': [5],  #
                   }
multi_feature = {'critical': [6, 7],
                 'ender_slayer': [6, 7],
                 'experience': [4, 5],
                 'giant_killer': [6, 7],
                 'life_steal': [4, 5],
                 'looting': [4, 5],
                 'luck': [6, 7],
                 'scavenger': [4, 5],
                 'sharpness': [6, 7],
                 'smite': [6, 7],
                 'syphon': [4, 5],
                 'thunderlord': [6, 7],
                 'titan_killer': [6, 7],
                 'vicious': [3, 5],
                 'growth': [6, 7],
                 'protection': [6, 7],
                 }

calc_feature = {'big_brain': [3, 5],
                'utlimate_inferno': [1, 5],
                'ultimate_legion': [1, 5],
                'ultimate_swarm': [1, 5],
                'ultimate_soul_eater': [1, 5],
                'mana_vampire': [1, 10]
                }

def set_noncalc_enchants_feature(feature_col, enchants_str, feature_map):
    enchants_list = feature_map[feature_col]
    for enchants_value in enchants_list:
        if enchants_value in enchants_str:
            return 1
        else:
            return 0


def set_calc_enchants_feature(feature_col, enchants_str, calc_feature_map):
    enchants_list = calc_feature_map[feature_col]
    for enchants_dict in enchants_list:
        for enchants_value in enchants_dict.keys():
            if enchants_value in enchants_str:
                return enchants_dict[enchants_value]
    return 0

def get_model_dataframe(file):
    df = pd.read_csv(file)
    reforge_features = ['giant', 'none', 'ancient', 'withered', 'fabled', 'loving', 'necrotic']
    #reforge_features = df.Reforge.unique().tolist()
    enchants_features = []

    df['Enchants'] = df['Enchants'].fillna('')
    df = df[df.Reforge.isin(reforge_features)]
    df = df.rename(columns={'Item Name': 'ItemName',
                            'Auction ID': 'AuctionID'})

    # calc features
    calc_feature_map = {}
    for feature in calc_feature.keys():
        feature_value_list = calc_feature[feature]
        feature_col = feature + str(feature_value_list[0]) + 'to' + str(feature_value_list[1])
        df[feature_col] = np.NaN
        enchants_features.append(feature_col)
        start_int = feature_value_list[0]
        moving_int = start_int
        enchants_list = []
        while moving_int <= feature_value_list[1]:
            enchants_list.append({feature + ' ' + str(moving_int): np.power(2, moving_int - start_int)})
            moving_int = moving_int + 1
        calc_feature_map[feature_col] = enchants_list

    for feature_col in calc_feature_map.keys():
        df[feature_col] = df['Enchants'].apply(lambda x: set_calc_enchants_feature(feature_col=feature_col,
                                                                                   enchants_str=x,
                                                                                   calc_feature_map=calc_feature_map))

    feature_map = dict()
    # add single feature columns
    for feature in single_features.keys():
        feature_value_list = single_features[feature]
        if len(feature_value_list) == 1:
            feature_col = feature + str(feature_value_list[0])
            feature_map[feature_col] = [feature + ' ' + str(feature_value_list[0])]
        else:
            feature_col = feature + str(feature_value_list[0]) + 'to' + str(feature_value_list[1])
            start_int = feature_value_list[0]
            enchants_list = []
            while start_int <= feature_value_list[1]:
                enchants_list.append(feature + ' ' + str(start_int))
                start_int = start_int + 1
            feature_map[feature_col] = enchants_list
        df[feature_col] = np.NaN
        enchants_features.append(feature_col)

    # add multi feature columns
    for feature in multi_feature.keys():
        feature_value_list = multi_feature[feature]
        start_int = feature_value_list[0]
        while start_int <= feature_value_list[1]:
            feature_col = feature + str(start_int)
            df[feature_col] = np.NaN
            enchants_features.append(feature_col)
            feature_map[feature_col] = [feature + ' ' + str(start_int)]
            start_int = start_int + 1

    for feature_col in feature_map.keys():
        df[feature_col] = df['Enchants'].apply(lambda x: set_noncalc_enchants_feature(feature_col=feature_col,
                                                                                      enchants_str=x,
                                                                                      feature_map=feature_map))

    reforge_data = df[['AuctionID', 'Price', 'Reforge']]
    reforge_data = reforge_data.assign(ReforgeValue=1)
    #reforge_data['ReforgeValue'] = 1
    reforge_data = reforge_data.pivot_table(index=['AuctionID', 'Price'], columns='Reforge', values='ReforgeValue')
    reforge_data = reforge_data.reset_index()
    for reforge_feature in reforge_features:
        if reforge_feature not in  reforge_data.columns.tolist():
            reforge_data[reforge_feature] = np.NaN
    reforge_data = reforge_data.fillna(0)

    enchants_data = df[['AuctionID'] + enchants_features]
    regress_data = reforge_data.merge(enchants_data, on=['AuctionID'])
    regress_feature = reforge_features + enchants_features

    return regress_data[['Price'] + regress_feature], regress_feature

def get_predict_model(model_train_input_file):
    regress_data, regress_feature = get_model_dataframe(model_train_input_file)

    regress_fit = sm.OLS(regress_data['Price'], regress_data[regress_feature]).fit()
    return regress_fit

def predict_price(model_train_input_file, model_predict_input_file):
    # build model
    regress_fit = get_predict_model(model_train_input_file)
    pred_data, pred_features = get_model_dataframe(model_predict_input_file)
    if 'Price' in pred_data.columns:
        pred_data = pred_data.drop('Price', axis=1)
    predictions = regress_fit.predict(pred_data)
    print(predictions)


    # pred_data = tmp1[reforge_features].drop_duplicates()
    # predictions = regress_fit.predict(pred_data)
    # predictions = pd.DataFrame(predictions)
    # predictions.columns = ['PredictedPrice']
    # predictions_all = pd.concat([predictions, pred_data], axis=1)
    # predictions_all = predictions_all.melt(id_vars='PredictedPrice', value_vars=list_feautres)
    # predictions_all = predictions_all[predictions_all.value == 1]
    # predictions_all = predictions_all.drop('value', axis=1)
    # predictions_all.columns = ['PredictedPrice', 'Feature']

    # print(predictions_all)


if __name__ == "__main__":
    predict_price('model_train_input.csv', 'model_predict_sample.csv', )
